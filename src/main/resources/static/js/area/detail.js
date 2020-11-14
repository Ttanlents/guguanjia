let  vm=new Vue({
    el:'.main-content',
    data:{
       area:{}
    },
    methods:{

    },
    created:function () {
       this.area=parent.layer.obj;
    }
});