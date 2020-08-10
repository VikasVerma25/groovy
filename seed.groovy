job('Job1') {
    description('launching pod on K8s and deploying code to webserver')
    steps {
        shell('sh /workspace/deploy.sh )
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
        shell('sh /workspace/test.sh')
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
