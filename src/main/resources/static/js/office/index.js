let vm=new Vue({
    el:'.main-content',
    data:{
        pageInfo:{},
        condition:{},
        result:{}
    },
    methods:{
        selectPage:function (pageNum = 1, pageSize = 5) {
            axios({
                url:`sysOffice/selectPage/${pageNum}/${pageSize}`,
                params:this.condition
            }).then(response=>{
                console.log(response.data.obj)
                if (response.data.success){
                    console.log("赋值成功！")
                    this.pageInfo=response.data.obj;
                    this.result=response.data.obj.list;
                }

            }).catch(error=>{
                layer.msg(error.message)
            });
        }
    },
    created:function () {
        this.selectPage();
    }
});