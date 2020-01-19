## Asurion Test 
This test is completed and follows the guidlines which were provided.

This test dont use any 3rd party library except okhttp. 
It includes custom image loading code which implements the caching of image.

To test different scenerios of buttons you have to change constant with the available options in HomeRepository.java file.

```
    Request request = okHttpProvider.buildRequest(Constant.allEnabled);
```

```
    allEnabled
    allDisabled
    chatOnly
    callOnly
```

This test includes Unit tests.

Thank you.