const path = require('path');
const webpack = require('webpack');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const ExtractTextPlugin = require('extract-text-webpack-plugin');
const CleanWebpackPlugin = require('clean-webpack-plugin');

const fs = require('fs');

const babelrc = JSON.parse(fs.readFileSync('./.babelrc'));
require('babel-register')(babelrc);

const nodeEnv = process.env.NODE_ENV || 'development';
const isPro = nodeEnv === "production";
console.log('当前运行环境：', nodeEnv);

let resolve = function (dir) {
    return path.join(__dirname, dir);
};

// =============插件配置（Start）====================
let plugins = [
    new CleanWebpackPlugin(['dist']), // 清理dist文件夹
    new ExtractTextPlugin('[name].css'),
    new webpack.optimize.CommonsChunkPlugin({ name: 'vendor' }), // 抽出公共模块
    new webpack.optimize.ModuleConcatenationPlugin(), // 减少闭包函数数量,将一些有联系的模块，放到一个闭包函数
    // 多页面应用设置
    new HtmlWebpackPlugin({
        title: '首页',
        filename: 'index.html',
        template: './index.html',
        chunks: ['vendor','app']  // chunks 默认会在生成的 html 文件中引用所有的 js 文件, 可以指定引入哪些特定的文件
    }),
    new HtmlWebpackPlugin({
        title: '登陆',
        filename: 'login.html',
        template: './login.html',
        chunks: ['vendor','login']
    })
];
isPro && plugins.push( // 生产环境插件
    new webpack.DefinePlugin({ 'process.env.NODE_ENV': JSON.stringify('production') }),
    new webpack.optimize.UglifyJsPlugin({compress:{ warnings: false}}),
);
!isPro && plugins.push( // 开发环境插件
    new webpack.DefinePlugin({ 'process.env.NODE_ENV': JSON.stringify('development') }),
    new webpack.HotModuleReplacementPlugin() // 热替换
);
// =============插件配置（End）====================

module.exports = {
    devtool: isPro ? 'none' : 'cheap-module-source-map',
    entry: {
        app: './src/main',
        login: './src/login/login'
    },
    output: {
        filename: '[name].bundle.js',
        path: path.resolve(__dirname, 'dist'),
        publicPath: '/',
        chunkFilename: '[name].[hash].js'
    },
    plugins: plugins,
    node: {
        fs: 'empty'
    },
    resolve: {
        extensions: ['.js', '.vue'],
        modules: [
            path.resolve(__dirname, 'node_modules'),
            path.join(__dirname, './src')
        ]
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
            exclude: /node_modules/,
            include: resolve('/')
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
            exclude: /node_modules/,
            include: resolve('src')
        }, {
            test: /\.(png|jpe?g|gif|svg)(\?.*)?$/,
            use:[{
                loader: "url-loader",
                options: {
                    limit: 1024,
                    name: '[name].[hash:7].[ext]',    // 将图片都放入img文件夹下，[hash:7]防缓存
                    outputPath: 'image/',    // where the img will go
                    publicPath: '../'   // override the default path
                }
            }]
        }, {
            test: /.(ttf|otf|eot|svg|woff(2)?)(\?[a-z0-9]+)?$/,
            use: [{
                loader: 'file-loader',
                options: {
                    name: '[name].[ext]',
                    outputPath: 'fonts/',    // where the fonts will go
                    publicPath: '../'   // override the default path
                }
            }]
        }, {
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
        port: 8080,
        hot: true,
        inline: true,
        proxy: {
            '/v1/*': {
                target: 'http://localhost/',
                secure: false,
                changeOrigin: true,
                pathRewrite: {'^/v1/': ''} // api路径重写(去掉这个属性，可用于图片等资源的转发)
            }
        }
    }
};