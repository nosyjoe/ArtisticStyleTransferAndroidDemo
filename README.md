# Artistic Style Transfer Android Demo
Demonstrates how to setup TensorFlow for artistic style transfer

## Android Demo App

Building the app should be straight-forward by opening the project in Android Studio or running `./gradlew assembleDebug` on the command line. Android SDK must be installed properly.

## Training a style 

tbw

## Links

### Artistic Style Transfer

* Original Paper: ["A Neural Algorithm of Artistic Style"](https://arxiv.org/abs/1508.06576), Leon A. Gatys, Alexander S. Ecker, Matthias Bethge, 2015
* [What Does A.I. Have To Do With This Selfie?](https://www.youtube.com/watch?v=WHmp26bh0tI) - YouTube video explaining the theory behind 
* [Google Magenta Multistyle Pastiche Generator](https://magenta.tensorflow.org/2016/11/01/multistyle-pastiche-generator/)


### Tensorflow

* [Tensorflow Mobile (Anroid, iOS)](https://www.tensorflow.org/mobile/)
* [Installing Tensorflow](https://www.tensorflow.org/install/install_linux)
* [Tensorflow for poets 1](https://codelabs.developers.google.com/codelabs/tensorflow-for-poets-1/) - Great introduction on how to use tensorflow on mobile and how to optimize models for mobile use.
* [Tensorflow for poets 2](https://codelabs.developers.google.com/codelabs/tensorflow-for-poets-2/) - More mobile application tipps & tricks

### Implementations

* [Logan Lengström's fast style transfer](https://github.com/lengstrom/fast-style-transfer) This is the one I ended up using since setting up the training was easy and the results great. It was also used to create the style.pb in this demo.
* Google's Magenta Model (allows mixing of styles): [GitHub](https://github.com/tensorflow/magenta/tree/master/magenta/models/image_stylization),  [blog post](https://magenta.tensorflow.org/2016/11/01/multistyle-pastiche-generator/) and [Google Code Lab](https://codelabs.developers.google.com/codelabs/tensorflow-style-transfer-android/index.html?index=..%2F..%2Findex#4)
* [neural-style](https://github.com/anishathalye/neural-style) has an implementation that allows tweaking of the result by specifying more parameters for the training process, like color-preserving or chaning the level of how abstract the result gets

### Other

* [Google Lab: Optimize models for mobile](https://codelabs.developers.google.com/codelabs/tensorflow-for-poets-2/#1)

