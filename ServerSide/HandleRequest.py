def upload(request):
    if request.method == 'POST':
        deviceID = measurement.deviceID = str(request.POST['deviceID'])
        return HttpResponse('Success!')
    else:
        return HttpResponse('Invalid Data')