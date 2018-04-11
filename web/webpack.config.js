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
    new webpack.optimize.CommonsChunkPlugin({ name: 'vendor' }),
    new webpack.optimize.ModuleConcatenationPlugin(), // 减少闭包函数数量,将一些有联系的模块，放到一个闭包函数
    // 多页面应用设置
    new HtmlWebpackPlugin({
        filename: 'index.html',
        template: './index.html',
        chunks: ['vendor','app']  // 过滤Filtering chunks, 只包括某些模块
    }),
    new HtmlWebpackPlugin({
        filename: 'login.html',
        template: './login.html',
        chunks: ['vendor','test']  // 过滤Filtering chunks, 只包括某些模块
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
        test: './src/login/login'
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
        port: 3055,
        hot: true,
        inline: true,
        stats: {
            assets: true,
            children: false,
            modules: false,
            chunks: false,
            publicPath: false,
            timings: true,
            warnings: true,
            colors: {
                green: '\u001b[32m'
            }
        }
    }
};