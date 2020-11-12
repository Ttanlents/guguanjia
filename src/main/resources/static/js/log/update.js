let vm=new Vue({
    el:'.main-content',
    data:{
        log:{}
    },
    methods:{

    },
    created:function () {
        this.log=parent.layer.obj;

    }
});