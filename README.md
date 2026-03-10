# Prysm 
![Prysm Logo](TMessagesProj/src/main/res/drawable-xxhdpi/prysm_logo.png)

A client based only on pure rainbow.

Prysm is an unofficial Telegram client for Android, forked from the official [Telegram source code](https://github.com/DrKLO/Telegram).

---

## Features

### Spy
- Display deleted messages — deleted messages remain visible, marked in gray
- Edit history — tap on an edited message to see all previous versions
- Transparent mode — makes the interface partially transparent

### Personalization
- Deleted messages appearance — choose between solid or semi-transparent style
- Download speed boost — three modes: off, fast, ultra
- Upload speed boost

### Debug
- Real-time CPU and memory usage
- Device and system information
- Stack trace recording

---

## Building

1. Clone the repository
```
git clone https://github.com/CrisPixar/Prysm.git
```

2. Get your API credentials at [my.telegram.org](https://my.telegram.org)

3. Add secrets to your GitHub repository:
   - `APP_ID`
   - `APP_HASH`

4. Push to master — GitHub Actions will build the APK automatically

---

## Based on

- [DrKLO/Telegram](https://github.com/DrKLO/Telegram) — official Telegram for Android source

---

## License

GPL-2.0
