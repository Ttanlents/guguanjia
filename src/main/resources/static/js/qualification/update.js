let vm=new Vue({
    el:'.main-content',
    data:{
        qualification:{}
    },
    /* es6方法语法给默认值，如果有参数就会覆盖默认值*/
    methods:{
        selectOne:function () {
            axios({
                url:`qualification/selectOne/${this.qualification.id}`,
            }).then(response=>{
                if (response.data.success){
                    console.log("打印下数据"+response.data.obj.id)
                    this.qualification=response.data.obj;
                }else{
                    layer.msg(response.data.msg)
                }
            }).catch(error=>{
                    layer.msg(error.message)
                }
            )
        },
        doUpdate:function (check) {
            this.qualification.check=check;
            this.qualification.address=null;
           //  parent.layer.success=response.data.success;
            axios({
                url:'qualification/doUpdate',
                method:'put',
                data: this.qualification
            }).then(response=>{
                if (response.data.success){
                    parent.layer.success=response.data.success;
                    let index=parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                }else{
                    layer.msg(response.data.msg)
                }
            }).catch(error=>{
                    layer.msg(error.message)
                }
            )

        }
    },
    created:function () {
        //从父窗口中取值
        this.qualification.id=parent.layer.obj;
        this.selectOne();
    }
});