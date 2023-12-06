const swalWithBootstrapButtons = Swal.mixin({
  customClass: {
    confirmButton: "btn btn-success",
    cancelButton: "btn btn-danger"
  },
  buttonsStyling: false
});
Confirm = {
  show: function (message, callback) {
    swalWithBootstrapButtons.fire({
      title: message,
      text: "操作不可逆！！！",
      icon: "warning",
      showCancelButton: true,
      confirmButtonText: "确认!",
      cancelButtonText: "取消!",
      reverseButtons: true
    }).then((result) => {
      if (result.isConfirmed) {
        if (callback){
          callback()
        }
      }
    })
  },
}
