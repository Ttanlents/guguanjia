let vm=new Vue({
    el:'.main-content',
    data:{
        office:{}
    },
    methods:{

    },
    created:function () {
        this.office=parent.layer.obj2;
    }
});