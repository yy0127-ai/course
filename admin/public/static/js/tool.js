Tool ={
  /**
   * 空校验null或""都返回true
   * @param value
   */
  isEmpty: function (obj) {
    if ((typeof obj == 'string')){
      return !obj || obj.replace(/\s+/g,"") == ""
    }else{
      return (!obj || JSON.stringify(obj) === "{}" || obj.length === 0)
    }
  },
  /**
   * 非空校验
   * @param obj
   * @returns {boolean}
   */
  isNotEmpty: function(obj){
    return !this.isEmpty()
  },
  /**
   * 长度校验
   * @param value
   * @param min
   * @param max
   */
  isLength(str, min, max){
    return $.trim(str).length >= min && $.trim(str).length <= max;
  }
};