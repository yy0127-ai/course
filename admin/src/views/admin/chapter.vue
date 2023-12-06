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
        <th>名称</th>
        <th>课程ID</th>
        <th>操作</th>

        <th></th>
      </tr>
      </thead>

      <tbody>
      <tr v-for="chapter in chapters">
        <td>{{chapter.id}}</td>
        <td>{{chapter.name}}</td>
        <td>{{chapter.courseId}}</td>

        <td>
          <div class="hidden-sm hidden-xs btn-group">
            <button v-on:click="edit(chapter)" class="btn btn-xs btn-info">
              <i class="ace-icon fa fa-pencil bigger-120"></i>
            </button>

            <button v-on:click="del(chapter.id)" class="btn btn-xs btn-danger">
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
            <h4 class="modal-title">大章章节</h4>
          </div>
          <div class="modal-body">
            <form class="form-horizontal">
              <div class="form-group">
                <label class="col-sm-2 control-label">名称</label>
                <div class="col-sm-10">
                  <input type="text" v-model="chapter.name" class="form-control"  placeholder="名称">
                </div>
              </div>
              <div class="form-group">
                <label class="col-sm-2 control-label">课程ID</label>
                <div class="col-sm-10">
                  <input type="text" v-model="chapter.courseId" class="form-control"  placeholder="课程ID">
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
    <pagination ref="pagination" v-bind:list="list"></pagination> <!--ref是别名，v-bind前面的list是分页组件暴露出来的一个回调方法，后面的list是chapter-->
  </div>
</template>
<script>
  import Pagination from "../../components/pagination";
  export default {
    components: {Pagination},
    name: 'chapter',
    data:function() {
      return {
        chapter:{},
        chapters: [],
      }
    },
    mounted: function(){
      //sidebar激活样式 方法一
      //this.$parent.activeSidebar("business-chapter-sidebar");
      let _this = this;
      _this.$refs.pagination.size = 5;  //初始化的时候定义页面大小为5
      _this.list(1);
    },
    methods: {
      add(){
        let _this = this;
        _this.chapter = {};
        $("#form-modal").modal("show")
      },
      edit(chapter){
        let _this = this;
        // _this.chapter = chapter;  //出现问题 在修改的时候 表格同时修改了 但是取消会在变成原来的样子
        _this.chapter = $.extend({}, chapter);  //jquery的一个方法 先将chapter复制到一个{} 然后对{}进行修改 然后在赋值给要传给后端的数据
        $('#form-modal').modal("show");//展示出模态框
      },
      list(page) {
        let _this = this;
        // Loading.show(); 我没有这个图片 呜呜呜
        _this.$ajax.post('http://127.0.0.1:9000/business/admin/chapter/list', {
          page: page,
          size: _this.$refs.pagination.size,  //获取子组件，$refs内置变量，根据pagination名字获取
        })
          .then(response=>{
            // Loading.hide();
            console.log("章列表查询返回的结果为", response);
            let resp = response.data;
            _this.chapters = resp.content.list;
            _this.$refs.pagination.render(page, resp.content.total);//重新渲染总条数
        })
      },

      save(){
        let _this = this;
        _this.$ajax.post('http://127.0.0.1:9000/business/admin/chapter/save', _this.chapter).then((response)=>{
          console.log("保存大章", response);
          let resp = response.data;
          if (resp.success){
            $("#form-modal").modal("hide");
            _this.list(1);
            toast.success("保存成功")
          }
        });
      },
      /**
       * 删除chapter 根据id删除
       * @param id
       */
      del(id){
        const swalWithBootstrapButtons = Swal.mixin({
          customClass: {
            confirmButton: "btn btn-success",
            cancelButton: "btn btn-danger"
          },
          buttonsStyling: false
        });
        swalWithBootstrapButtons.fire({
          title: "确认删除吗?",
          text: "操作不可逆!",
          icon: "warning",
          showCancelButton: true,
          confirmButtonText: "确认!",
          cancelButtonText: "取消!",
          reverseButtons: true
        }).then((result) => {
          if (result.isConfirmed) {
            let _this = this;
            _this.$ajax.delete('http://127.0.0.1:9000/business/admin/chapter/delete/' + id).then((response) => {
              console.log("删除大章列表结果:", response);
              let resp = response.data;
              if (resp.success){
                _this.list(1);
                toast.success("删除成功！")
              }
            })
          }
        });
      },
    }
  }
</script>