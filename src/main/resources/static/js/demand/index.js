let vm=new Vue({
    el:'.main-content',
    data:{
        result:{},
        pageInfo:{}
    },
    methods:{
        selectPage:function (pageNum=1,pageSize=5) {
            axios({
                url:`demand/selectPage/${pageNum}/${pageSize}`
            }).then(response=>{
                console.log(response.data)
                this.pageInfo=response.data.obj;
                this.result=response.data.obj.list;
            }).catch(error=>{
                layer.msg(error.message)
            })
        },
        toUpdate:function (demand) {
            //iframe窗
            layer.obj = demand;
            layer.open({
                type: 2,
                title: false,
                area: ['80%', '80%'],
                content: ['demand/toUpdate'],
                end:()=> { //此处用于演示

                   if (layer.success!=undefined&&layer.success){
                        layer.msg("更新完成!");
                        layer.success=false;
                        this.selectPage() //刷星页面

                    }


                }
            })
        },
        toDetail:function (demand) {
            layer.obj = demand;
            layer.open({
                type: 2,
                title: false,
                area: ['80%', '80%'],
                content: ['demand/toDetail'],
                end:()=> { //此处用于演示




                }
            })
        }
    },
    created : function() {
        this.selectPage();
    },

});