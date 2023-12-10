Validator = {
  require: function (value, text) {
    if(Tool.isEmpty(value)){
      Toast.warning(text + "不能为空");
      return false;
    }else{
      return true;
    }
  },
  length: function (value, text, min, max) {
    if(!Tool.isLength(value, min, max)) {
      Toast.warning(text + "长度" + min + "~" + max);
      return false;
    }else{
      return true;
    }
  }
};