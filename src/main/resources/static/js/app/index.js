var vm=new Vue({
    el:'.main-content',
    data:{
        result: {},
        pageInfo: {},
        app:{},
        active:true
    },
    methods:{
        selectPage:function (pageNums=1,pageSize=5) {

            axios({
                url:`manager/app/selectPage/${pageNums}/${pageSize}`,
                // params:{""},
               // data:{"page":_this.page,"name":_this.name}
            }).then(response=> {
                console.log(response.data)
                this.result=response.data.obj.list;
                this.pageInfo=response.data.obj;

            }).catch(function (reason) {
                console.log(reason)
            });
        },
        toUpdate: function (app) {
            //iframe窗
            layer.obj = app;
            layer.open({
                type: 2,
                title: false,
                // closeBtn: 0, //不显示关闭按钮
                //shade: [0],
                area: ['80%', '80%'],
                // offset: 'rb', //右下角弹出
                // time: 2000, //2秒后自动关闭
                // anim: 2,
                content: ['toUpdate'], //iframe的url，no代表不显示滚动条
                end:()=> { //此处用于演示
                    //layer.open({
                        //type: 2,
                        // title: '很多时候，我们想最大化看，比如像这个页面。',
                        // shadeClose: true,
                        //shade: false,
                        // maxmin: true, //开启最大化最小化按钮
                        // area: ['893px', '600px'],
                        // content: '//fly.layui.com/'
                      if (layer.obj2.msg==0){
                          layer.msg("更新完成!")
                          this.selectPage() //刷星页面
                      }

                   // });
                }
            })
        },
        doInsert:function (app) {
            axios({
                url:'doInsert',
                method:'post',
                data:app,
            }).then(response=>{
                console.log(response.data)
                if (response.data.success){
                    //1.清空列表
                    this.app={}
                    //2.切换到左边的ui
                    this.active=!this.active;
                }
            }).catch(error=>{
                layer.msg(error.message)
            });
        },
        changeActive:function (number) {
            console.log(number)
           if (number==1){
               this.active=true;
           }else {
               this.active=false;
           }
        },
        doDelete:function (app) {
            axios({
                url:'doDelete',
                method:'post',
                data:app,
            }).then(response=>{
                console.log(response.data)
                if (response.data.success){
                    layer.msg(response.data.msg)
                    this.selectPage();
                }else {
                    layer.msg(response.data.msg)
                }
            }).catch(error=>{
                layer.msg(error.message)
            });
        }

    },
    created : function() {
        this.selectPage();
    }
});
