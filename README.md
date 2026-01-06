<div>
  <img style="100%" src="https://capsule-render.vercel.app/api?type=waving&height=100&section=header&reversal=false&fontSize=70&fontColor=FFFFFF&fontAlign=50&fontAlignY=50&stroke=-&descSize=20&descAlign=50&descAlignY=50&color=gradient"  />
</div>

## **New Year Countdown App 🎇**

A modern and high-performance Android application built with **Kotlin** and **Jetpack Compose** for the UI. This app features a real-time countdown to the New Year with smooth transitions and a custom particle-based firework celebration.

### **✨ Features**

1. **Real-Time Countdown**: Accurate calculation of days, hours, minutes, and seconds using the `java.time` API.  
2. **Theme Toggle**: Seamless transition between Light and Dark mode with a single tap.  
3. **Fireworks**: Fireworks automatically appear when the countdown is done. All particle animations are built using `Compose Canvas` and `withFrameNanos`.

### **🛠 Tech Stack**

* **Language**: `Kotlin`
* **IDE**: `Android Studio`
* **UI**: `Jetpack Compose (Material 3) ` 
* **Concurrency**: `Kotlin Coroutines` for the timer loop  
* **Animation**: `Compose Animation API` & `Custom Canvas Drawing`

### **📂 Project Structure**

```
com.example.newyear  
├── logic  
│   └── CountdownCalculator.kt      # Business logic & time formatting  
├── state  
│   └── CountdownState.kt           # UI State data class  
├── theme  
│   ├── Color.kt                    # Definition for the colors  
│   ├── Theme.kt                    # Custom theme wrapper  
│   └── Type.kt                     # Custom Typography  
├── ui  
│   ├── components  
│   │   ├── AbstractShapes.kt       # Canvas-based background shapes  
│   │   ├── CountdownTimer.kt       # Timer UI components  
│   │   └── FireworksAnimation.kt   # Fireworks particle system  
│   └── screens  
│       └── CountdownScreen.kt      # Main UI layout  
└── MainActivity.kt                 # Entry point & theme state management
```

### **🚀 Getting Started**

#### **Prerequisites**

* `Android Studio Ladybug | 2024.2.1 or newer.`  
* `JDK 17+`
* `Android SDK 34+`

#### **Installation**

1. Clone the repository:
   ```
   git clone github.com/arkaceananda/NewYearCountdown
   ```

3. Open the project in **Android Studio**.  
4. Let Gradle sync and build the project.  
5. Run the app on an emulator or physical device.

### **🧪 Testing the Celebration**

To see the fireworks and the "Happy New Year" message immediately, you can modify the ``getTargetTime`` function in ``CountdownCalculator.kt``:



```
fun getTargetTime(): LocalDateTime {  
    // Set target to 2 minutes to test
    return LocalDateTime.now().plusMinutes(2)  
}
```

### **📜 License**

This project is licensed under the MIT License - see the [LICENSE](https://github.com/maurodesouza/profile-readme-generator?tab=readme-ov-file) file for details.

<div align="center">
  <img src="https://count.getloli.com/@:arkaceananda?theme=3d-num&padding=7&scale=1&align=top&pixelated=1&darkmode=auto"  />
</div>

</br>
<div align="center">
  <img src="https://github-readme-activity-graph.vercel.app/graph?username=arkaceananda&radius=16&theme=react&area=true&order=5" height="300" alt="activity-graph graph"  />
</div>
</br>

<p align="center" font>Built by Arka Aceananda</p>

<div>
  <img style="100%" src="https://capsule-render.vercel.app/api?type=waving&height=100&section=footer&reversal=false&fontSize=70&fontColor=FFFFFF&fontAlign=50&fontAlignY=50&stroke=-&descSize=20&descAlign=50&descAlignY=50&color=gradient"  />
</div>
