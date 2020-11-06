let vm=new Vue({
    el:'.main-content',
    data:{
        area:{},
        params:{}
    },
    /* es6方法语法给默认值，如果有参数就会覆盖默认值*/
    methods:{
        doUpdate:function () {
            this.params.area=this.area;
            axios({
                url:'sysArea/doUpdate',
                method:'put',
                data: this.params
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

        },
        doChancel:function(){
            let index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index)
        },
        doDelete:function(){

        },
        toSelect:function () {
            layer.aid=this.area.parentId;
            console.log(layer.obj);
            layer.open({
                type: 2,
                title: false,
                area: ['100%', '100%'],
                content: ['sysArea/toSelect'],
                end:()=> { //此处用于演示
                    if (layer.parentName!=undefined&&layer.parentName.length>0){
                        this.area.parentId=layer.aid;
                        this.area.parentName=layer.parentName;
                        this.area.parentIds=layer.parentIds;
                    }

                }
            })
        },
        toModule:function () {
            layer.open({
                type: 2,
                title: false,
                area: ['100%', '100%'],
                content: ['sysArea/toModule'],
                end:()=> { //此处用于演示
                    if (layer.icon!=undefined&&layer.icon.length>0){
                        this.area.icon=layer.icon;
                    }

                }
            })
        }
    },
    created:function () {
        //从父窗口中取值
        this.area=parent.layer.obj;
        this.params.oldParentIds=parent.layer.obj.parentIds;
    }
});