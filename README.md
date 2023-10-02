# 新xenmc loader计划

## 原理
- 通过mojang的json修改实现注入
- 主要是修改mainClass
- 修改libraries
- 添加injectVersion 为我的世界版本
- id为xenmc-${minecraft_version}-${xenmc_version}
- jar为自动下载的游戏版本
- time为下载日期
- arguments为注入的游戏参数

## 计划
- [ ] xenmc-gradle
  - [ ] 下载lib和assets
  - [ ] 自定义mapping源
  - [ ] 自定义launch Main
  - [ ] 使用 asm mixin bytebuddy [选择性加载，必定加载asm]
  - [ ] 读配置文件的lib目录的链接自动下载文件(本地文件jarInJar)
- [ ] xenmc-launch
  - [ ] 代替mc主类运行
- [ ] xenmc-loader
  - [ ] 读取配置文件为xenmc.json xenmc.md xenmc.toml xemmc.settings xenmc.properties
  - [ ] 写一套框架方便从resources快速访问，写一套快速读存文件的方案
  - [ ] 自建antlr框架读取并生生动态类注入进mc(多语言开发用)
- [ ] xenmc-kernel
  - [ ] 实现OrientedboundingBox包围盒碰撞体系(OBB)
  - [ ] 实现Fixed directions hulls或k-DOP固定方向凸包碰撞体系(OBB)
  - [ ] 实现Vertical-agliened OBB包围盒碰撞体系(OBB Plus)
  - [ ] 实现Sphere-Sphere包围盒碰撞体系(Sphere)
  - [ ] 实现Bounding Sphere包围球碰撞体系
  - [ ] 实现Bounding Frustum包围锥碰撞体系
  - [ ] 实现Frustum-AABB包围盒体系去尽量替代aabb(AABB plus)
  - [link](https://blog.csdn.net/Windgs_YF/article/details/87884884)
- [ ] xenmc-lanuage-enable
  - [ ] 通过类里面的变量来实现多语言的支持(覆写配置实现启用多语言)
- [ ] nucleoplasm
  - [ ] 核素的xenmc-loader系列，示例教程
- [ ] xenmc-install
  - [ ] 使用kotlin等多种方式构建安装包
  
