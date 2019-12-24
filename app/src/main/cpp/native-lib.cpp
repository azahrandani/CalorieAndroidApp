#include <jni.h>
#include <string>
#include <math.h>
#include <string.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_lab_calorie_activity_AnimationActivity_calculateBMI(
        JNIEnv* env,
        jobject /* this */,
        jint height,
        jint weight
        ) {
    float heightInCm = (float)height / 100;
    float powerOfHeight = pow(heightInCm, 2.0);
    float bmi = weight / powerOfHeight;
    char returnString[10];
    sprintf(returnString , "%.2lf" , bmi);
    return env->NewStringUTF(returnString);
}

