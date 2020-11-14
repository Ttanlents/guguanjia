let  vm=new Vue({
    el:'.main-content',
    data:{
        user:{}
    },
    methods:{

    },
    created:function () {
        this.user=parent.layer.obj;
    }
});