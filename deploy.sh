if ls /workspace | grep .html
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
fi