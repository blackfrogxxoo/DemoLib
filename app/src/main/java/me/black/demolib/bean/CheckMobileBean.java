package me.black.demolib.bean;

public class CheckMobileBean extends BaseBean<CheckMobileBean.Data> {
    public static class Data {

    }

    public static class Request {
        public String mobile;
        public Request(String mobile) {
            this.mobile = mobile;
        }
    }
}
