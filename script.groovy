job('Job1') {
    description('launching pod on K8s and deploying code to webserver')
    steps {
        shell(''' if ls /workspace | grep .html
        then        
            echo "using httpd image to deploy"
            if sudo kubectl get pods | grep webpage
            then
            echo "alraedy running"
            else
            sudo kubectl create -f /workspace/webpage.yaml
            sleep 25            
            podname=$(sudo kubectl get pod -l app=webpage -o jsonpath="{.items[0].metadata.name}")
            sudo kubectl cp /workspace/*.html $podname:/usr/local/apache2/htdocs/
            fi
        else 
        echo "Not an html file"
fi ''')
    }
}
job('Job2') {
    description('Testing the code and send notification mail')
    triggers {       
        upstream {
            upstreamProjects('job1')
            threshold('SUCCESS')
        }
    }
    steps {
        shell(''' status=$(curl -o /dev/null -s -w "%{http_code}" http://192.168.99.101:30303)
if [[ $status==200 ]]
then
    sudo python3 /workspace/success.py
else
    sudo python3 /workspace/fail.py
fi ''')
    }
}

buildPipelineView('Task 6') {
    filterBuildQueue()
    filterExecutors()
    title('deploy webpage over k8s cluster')
    displayedBuilds(5)
    selectedJob('Job1')
    alwaysAllowManualTrigger()
    showPipelineParameters()
    refreshFrequency(60)
}
