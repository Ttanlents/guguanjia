let vm=new Vue({
    el:'.main-content',
    data:{
        menu:{},
        params:{}
    },
    methods:{
        doUpdate:function () {
            this.params.menu=this.menu;
            axios({
                url:'sysResource/doUpdate',
                method:'put',
                data:this.params
            }).then(response=>{
                if (response.data.success){
                    let index = parent.layer.getFrameIndex(window.name);
                    parent.layer.close(index);
                    parent.layer.success=true;
                }
            }).catch(error=>{
                layer.msg(error.message)
            });
        },
        toSelect:function(id){
            layer.obj=id;
            layer.open({
                type: 2,
                title:false,
                area:['80%','80%'],
                content:['sysResource/toSelect'],
                end:()=>{
                    if (layer.success!=undefined&&layer.success){
                        this.menu.parentId=layer.resourceId;
                        this.menu.parentName=layer.resourceName;
                        this.menu.parentIds=layer.resourceParentIds;
                        layer.msg('选着完成');
                        layer.success=false;
                    }
                }
            });
        },
        toIcon:function () {
            layer.open({
                type: 2,
                title: false,
                area: ['100%', '100%'],
                content: ['sysResource/toIcon'],
                end:()=> { //此处用于演示
                    if (layer.icon!=undefined&&layer.icon.length>0){
                        this.menu.icon=layer.icon;
                        console.log(layer.icon);
                    }

                }
            })
        },
        doClose:function () {
            let index = parent.layer.getFrameIndex(window.name);
            parent.layer.close(index);
            parent.layer.success=false;
        }
    },
    created:function () {
        this.menu=parent.layer.obj;
        this.params.oldParentIds=this.menu.parentIds;
    }
});