status=$(curl -o /dev/null -s -w "%{http_code}" http://192.168.99.101:30303)
if [[ $status==200 ]]
then
    python3 /workspace/success.py
else
    python3 /workspace/fail.py
fi