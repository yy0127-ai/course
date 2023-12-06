Toast = {  //全局变量 存在三个方法 每个方法接收一个message，在需要使用的地方直接使用toast.方法
  success: function (message) {
    Swal.fire({
      position: "top-end",
      icon: "success",
      title: message,
      showConfirmButton: false,
      timer: 3000
    });
  },
  error: function (message) {
    Swal.fire({
      position: "top-end",
      icon: "error",
      title: message,
      showConfirmButton: false,
      timer: 3000
    });
  },
  warning: function (message) {
    Swal.fire({
      position: "top-end",
      icon: "warning",
      title: message,
      showConfirmButton: false,
      timer: 3000
    });
  },
}