let vm=new Vue({
    el:'.page-content',
    data:{
        demand:{}
    },
    /* es6方法语法给默认值，如果有参数就会覆盖默认值*/
    methods:{
        doUpdate:function () {
            axios({
                url:'demand/doUpdate',
                method:'put',
                data:this.demand
            }).then(response=>{
                if (response.data.success){
                    layer.msg(response.data.msg)
                    parent.layer.obj2={"msg":0}
                    let index=parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                }else{
                    layer.msg(response.data.msg)
                }
            }).catch(error=>{
                layer.msg(error.message)
                }
            )
        },
        doClose:function () {
            let index=parent.layer.getFrameIndex(window.name);
            console.log(index);
            parent.layer.obj2={"msg":1}
            parent.layer.close(index);
        }
    },
    created:function () {
        //从父窗口中取值
        this.demand=parent.layer.obj;
    }
});