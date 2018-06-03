const path = require('path');
const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const CleanWebpackPlugin = require('clean-webpack-plugin');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const UglifyJsPlugin = require('uglifyjs-webpack-plugin');

const nodeEnv = process.env.NODE_ENV || 'development';
const isPro = nodeEnv === "production";

console.log("当前目录:", __dirname);
console.log('运行环境:', nodeEnv);

let resolve = function (dir) {
    return path.join(__dirname, dir);
};

// =============插件配置（Start）====================
let plugins = [
    new CleanWebpackPlugin(['dist']), // 清理dist文件夹
    new ExtractTextPlugin({filename: 'css/[name].[hash:5].css', allChunks: true}),
    new webpack.optimize.CommonsChunkPlugin({ name: 'vendor' }),
    new webpack.optimize.ModuleConcatenationPlugin(), // 减少闭包函数数量,将一些有联系的模块，放到一个闭包函数
    new webpack.ProvidePlugin({
        $: "jquery",
        jQuery: "jquery",
        jquery: "jquery",
        "window.jQuery": "jquery"
    }),
    new HtmlWebpackPlugin({
        filename: 'index.html',
        template: './index.html',
        chunks: ['vendor','app']   // chunks 默认会在生成的 html 文件中引用所有的 js 文件, 可以指定引入哪些特定的文件
    })
];
isPro && plugins.push( // 生产环境插件
        new webpack.DefinePlugin({ 'process.env.NODE_ENV': JSON.stringify('production') }),
        new UglifyJsPlugin({ uglifyOptions:{compress:{warnings:false}}, sourceMap:true }),
);
!isPro && plugins.push( // 开发环境插件
        new webpack.DefinePlugin({ 'process.env.NODE_ENV': JSON.stringify('development') }),
        new webpack.HotModuleReplacementPlugin() // 热替换
);
// =============插件配置（End）====================

module.exports = {
    devtool: isPro ? 'none' : 'cheap-module-source-map',
    entry: {
        app: './src/main'
    },
    output: {
        filename: '[name].bundle.js',
        path: resolve('dist'),
        publicPath: '/',
        chunkFilename: '[name].[hash].js'
    },
    plugins: plugins,
    node: {
        fs: 'empty'
    },
    resolve: {
        extensions: ['.js', '.vue'],
        alias: {
            'vue$': 'vue/dist/vue.esm.js'
        }
    },
    module: {
        rules: [{
            test: /\.js$/,
            use: [{
                loader: 'babel-loader',
                options: {
                    plugins:['syntax-dynamic-import']
                }
            }],
            include: [resolve('src'), resolve('src'), resolve('node_modules/element-ui/src'), resolve('node_modules/element-ui/packages')]
        },{
            test: /\.vue$/,
            use: [{
                loader: 'vue-loader',
                options: {
                    loaders: {
                        css: 'style-loader!css-loader',
                        scss: 'vue-style-loader!css-loader!sass-loader',
                        sass: 'vue-style-loader!css-loader!sass-loader?indentedSyntax'
                    },
                    plugins:['syntax-dynamic-import']
                }
            }],
            include: resolve('src')
        },{
            test: /\.(png|jpe?g|gif)(\?.*)?$/,
            use:[{
                loader: "url-loader",
                options: {
                    limit: 1024,
                    name: '[name].[hash:7].[ext]',    // 将图片都放入img文件夹下，[hash:7]防缓存
                    outputPath: 'image/',    // where the img will go
                    publicPath: '/image/'   // override the default path
                }
            }]
        },{
            test: /.(ttf|otf|eot|svg|woff(2)?)(\?[a-z0-9]+)?$/,
            use: [{
                loader: 'file-loader',
                options: {
                    name: '[name].[ext]',
                    outputPath: 'fonts/',    // where the fonts will go
                    publicPath: '/fonts/'   // override the default path
                }
            }]
        },{
            test: /\.css$/,
            loader: ExtractTextPlugin.extract('css-loader')
        },{
            test: /\.scss/,
            loader: ExtractTextPlugin.extract('css-loader!sass-loader')
        }]
    },
    devServer: {
        contentBase: resolve('/'),
        historyApiFallback: true,
        compress: true,
        port: 3077,
        hot: true,
        inline: true,
        proxy: {
            '/v1/*': {
                target: 'http://127.0.0.1:8080/',
                secure: false,
                changeOrigin: true,
                pathRewrite: {'^/v1/': ''} // api路径重写(去掉这个属性，可用于图片等资源的转发)
            }
        }
    }
};