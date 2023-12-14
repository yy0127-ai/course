<template>
  <div>
    <p>
      <button v-on:click="list(1)" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-refresh"></i>
        刷新
      </button>
      &nbsp;
      <button v-on:click="add()" class="btn btn-white btn-default btn-round">
        <i class="ace-icon fa fa-edit"></i>
        新增
      </button>
    </p>
    <table id="simple-table" class="table  table-bordered table-hover">
      <thead>
      <tr>
        <th>ID</th>
        <th>标题</th>
        <th>课程</th>
        <th>大章</th>
        <th>视频</th>
        <th>时长</th>
        <th>收费</th>
        <th>顺序</th>
        <th>创建时间</th>
        <th>修改时间</th>
        <th>操作</th>
      </tr>
      </thead>
      <tbody>
      <tr v-for="section in sections">
          <td>{{section.id}}</td>
          <td>{{section.title}}</td>
          <td>{{section.courseId}}</td>
          <td>{{section.chapterId}}</td>
          <td>{{section.video}}</td>
          <td>{{section.time}}</td>
          <td>{{section.charge}}</td>
          <td>{{section.sort}}</td>
          <td>{{section.createdAt}}</td>
          <td>{{section.updatedAt}}</td>
        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button v-on:click="edit(section)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>

            <button v-on:click="del(section.id)" class="btn btn-xs btn-danger">
              <i class="ace-icon fa fa-trash-o bigger-120"></i>
            </button>
          </div>
        </td>
      </tr>
      </tbody>
    </table>
    <div id="form-modal" class="modal fade" tabindex="-1" role="dialog">
      <div class="modal-dialog" role="document">
        <div class="modal-content">
          <div class="modal-header">
            <button type="button" class="close" data-dismiss="modal" aria-label="Close"><span aria-hidden="true">&times;</span></button>
            <h4 class="modal-title">小节章节</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
                <div class="form-group">
                  <label class="col-sm-2 control-label">ID</label>
                  <div class="col-sm-10">
                    <input type="text" v-model="section.id" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">标题</label>
                  <div class="col-sm-10">
                    <input type="text" v-model="section.title" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">课程</label>
                  <div class="col-sm-10">
                    <input type="text" v-model="section.courseId" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">大章</label>
                  <div class="col-sm-10">
                    <input type="text" v-model="section.chapterId" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">视频</label>
                  <div class="col-sm-10">
                    <input type="text" v-model="section.video" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">时长</label>
                  <div class="col-sm-10">
                    <input type="text" v-model="section.time" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">收费</label>
                  <div class="col-sm-10">
                    <input type="text" v-model="section.charge" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">顺序</label>
                  <div class="col-sm-10">
                    <input type="text" v-model="section.sort" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">创建时间</label>
                  <div class="col-sm-10">
                    <input type="text" v-model="section.createdAt" class="form-control">
                  </div>
                </div>
                <div class="form-group">
                  <label class="col-sm-2 control-label">修改时间</label>
                  <div class="col-sm-10">
                    <input type="text" v-model="section.updatedAt" class="form-control">
                  </div>
                </div>
            </form>
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-default" data-dismiss="modal">关闭</button>
            <button type="button" v-on:click="save()" class="btn btn-primary">保存</button>
          </div>
        </div><!-- /.modal-content -->
      </div><!-- /.modal-dialog -->
    </div><!-- /.modal -->
    <pagination ref="pagination" v-bind:list="list"></pagination> <!--ref是别名，v-bind前面的list是分页组件暴露出来的一个回调方法，后面的list是section-->
  </div>
</template>
<script>
  import Pagination from "../../components/pagination";
  export default {
    components: {Pagination},
    name: 'section',
    data:function() {
      return {
        section:{},
        sections: [],
      }
    },
    mounted: function(){
      //sidebar激活样式 方法一
      //this.$parent.activeSidebar("business-section-sidebar");
      let _this = this;
      _this.$refs.pagination.size = 5;  //初始化的时候定义页面大小为5
      _this.list(1);
    },
    methods: {
      add(){
        let _this = this;
        _this.section = {};
        $("#form-modal").modal("show")
      },
      edit(section){
        let _this = this;
        // _this.section = section;  //出现问题 在修改的时候 表格同时修改了 但是取消会在变成原来的样子
        _this.section = $.extend({}, section);  //jquery的一个方法 先将section复制到一个{} 然后对{}进行修改 然后在赋值给要传给后端的数据
        $('#form-modal').modal("show");//展示出模态框
      },
      list(page) {
        let _this = this;
        // Loading.show(); 我没有这个图片 呜呜呜
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/section/list', {
          page: page,
          size: _this.$refs.pagination.size,  //获取子组件，$refs内置变量，根据pagination名字获取
        })
          .then(response=>{
            // Loading.hide();
            let resp = response.data;
            _this.sections = resp.content.list;
            _this.$refs.pagination.render(page, resp.content.total);//重新渲染总条数
        })
      },

      save(){
        let _this = this;
        _this.$ajax.post(process.env.VUE_APP_SERVER + '/business/admin/section/save', _this.section).then((response)=>{
          let resp = response.data;
          if (resp.success){
            $("#form-modal").modal("hide");
            _this.list(1);
            Toast.success("保存成功");
          }else{
            Toast.warning(resp.message);
          }
        });
      },
      /**
       * 删除section 根据id删除
       * @param id
       */
      del(id){
        let _this = this;
        Confirm.show("确认删除吗?", function () {
          _this.$ajax.delete(process.env.VUE_APP_SERVER + '/business/admin/section/delete/' + id).then((response) => {
            let resp = response.data;
            if (resp.success){
              _this.list(1);
              Toast.success("删除成功！")
            }
          })
        })
      },
    }
  }
</script>