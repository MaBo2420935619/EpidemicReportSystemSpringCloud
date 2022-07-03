
module.exports = {
    devServer: {
        open: process.platform === "darwin",
        disableHostCheck: false,
        host: "0.0.0.0",
        port: 8081,
        https: false,
        hotOnly: false, // See https://github.com/vuejs/vue-cli/blob/dev/docs/cli-service.md#configuring-proxy
        proxy: null
    },
    lintOnSave: false   // 取消 eslint 验证
};
module.exports = {
    configureWebpack: {
        externals: {
            'vue': 'Vue',
            'vuex': 'Vuex',
            'vue-router': 'VueRouter',
            'mint-ui': 'MintUI',
            'echarts': 'echarts',
        }
    }
}
