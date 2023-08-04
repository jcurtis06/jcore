# jcore
<p align="center">
    <img src="assets/jcore_logo.png" alt="jcore logo">
</p>
<p align="center">
    <a href="https://jitpack.io/#jcurtis06/jcore">
        <img src="https://jitpack.io/v/jcurtis06/jcore.svg" alt="jitpack">
    </a>
</p>

## 2D cross-platform game library 
**jcore is a open-source game library that doesn't restrict game developers.** It provides basic features needed to develop games while still allowing for flexibility. jcore is built on the JVM, so projects can be exported to **Mac, Linux, Windows, and anywhere else that can run Java.**

## Getting the engine
Using jcore in your project is easy.

**Step 1:** Add JitPack to your repositories
```groovy
allprojects {
    repositories {
        maven { url 'https://jitpack.io' }
    }
}
```
**Step 2:** Add jcore as a dependency
```groovy
dependencies {
    implementation 'com.github.jcurtis06:jcore:1.0.0'
}
```

_Make sure to use the latest version of jcore_

## Credits
- [Jonathan Curtis (jcurtis06)](https://www.github.com/jcurtis06) - Creator
- [Eli Bradshaw (RidgeRider)](https://www.github.com/RidgeRider7) - Creator
- [JitPack](https://jitpack.io) - Dependency management
- [Box2D](https://box2d.org) - Physics engine
