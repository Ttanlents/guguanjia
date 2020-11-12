let vm=new Vue({
    el:'.main-content',
    data:{
       m:{}
    },
    methods:{

    },
    created:function () {
        this.m=parent.layer.obj;
    }
});