var vm=new Vue({
    el:'.main-content',
    data:{
        pageInfo: {},
        sysLog:{type:''}
    },
    methods:{
        selectPage:function (pageNums=1,pageSize=5) {
            console.log(this.sysLog);

            axios({
                url:`sysLog/selectPage/${pageNums}/${pageSize}`,
                 params:this.sysLog,
                // data:{"page":_this.page,"name":_this.name}
            }).then(response=> {
                console.log(response.data.obj)
                this.pageInfo=response.data.obj;

            }).catch(function (reason) {
                console.log(reason)
            });
        },
        toUpdate: function (log) {
           layer.obj=log;
           layer.open({
               type: 2,
               title: false,
               area:['80%','80%'],
               content:['sysLog/toUpdate'],
               end:()=>{
                   if (layer.success){
                       layer.msg('更新完成！')
                   }
               }
           });
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

        doDelete:function (log) {
            layer.confirm('确定要永久删除该条记录吗？',{
                btn:['确定','取消'],
                title:"提示"
            },()=>{
                axios({
                    url:'sysLog/doDelete',
                    method:'post',
                    data:log,
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
            })
        }

    },
    created : function() {
        this.selectPage();
    }
});
