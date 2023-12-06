const Toast = Swal.mixin({
  toast: true,
  position: "top-end",
  showConfirmButton: false,
  timer: 3000,
  timerProgressBar: true,
  didOpen: (toast) => {
    toast.onmouseenter = Swal.stopTimer;
    toast.onmouseleave = Swal.resumeTimer;
  }
});

toast = {  //全局变量 存在三个方法 每个方法接收一个message，在需要使用的地方直接使用toast.方法
  success: function (message) {
    Toast.fire({
      icon: "success",
      title: message
    });
  },
  error: function (message) {
    Toast.fire({
      icon: "error",
      title: message
    });
  },
  warning: function (message) {
    Toast.fire({
      icon: "warning",
      title: message
    });
  },
}