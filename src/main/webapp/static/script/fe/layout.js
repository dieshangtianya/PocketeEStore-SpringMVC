(function () {
    $(document).ready(function () {
       $('#btn-exist').on('click',function () {
           $.post(APPHelper.APIMap.CUSTOMER_LOGOUT).then(function () {
               window.location.reload();
           })
       });
    });
})()