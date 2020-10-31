let vm=new Vue({
    el:'.tabbable',
    data:{
        result:{},
        pageInfo:{},
        condition:{type:'',check:''}
    },
    methods:{
        selectPage:function (pageNum=1,pageSize=5) {
            axios({
                url:`qualification/selectPage/${pageNum}/${pageSize}`,
                method:'get',
                params:this.condition
            }).then(response=>{
                console.log(response.data)
                this.pageInfo=response.data.obj;
                this.result=response.data.obj.list;
                console.log("赋值成功！")
            }).catch(error=>{
                layer.msg(error.message)
            })
        },
        toCondition:function (demand) {
            this.selectPage();
        },
        toSelectAll:function () {
            this.condition={type:'',check:''};
           this.selectPage();
        },
        toUpdate:function (id) {
            //iframe窗
            layer.obj = id;
            layer.open({
                type: 2,
                title: false,
                area: ['80%', '80%'],
                content: ['qualification/toUpdate'],
                end:()=> { //此处用于演示
                    console.log("layer.success"+layer.success)
                    if (layer.success!=undefined&&layer.success){
                        layer.msg("更新完成!")
                        this.selectPage() //刷星页面
                    }

                }
            })
        }
    },
    created : function() {
        this.selectPage();
    },

});