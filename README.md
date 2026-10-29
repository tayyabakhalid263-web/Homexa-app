# Homexa — Home Services Booking App

Android app (Java + XML) for booking home services.

## Features
- Login / Sign Up with **SQLite** (local database)
- Providers & bookings stored in **SQLite**
- Home, Services, Details, Booking, My Bookings screens
- JazzCash option is UI-only (no real payment)

## Tools
- Android Studio
- JDK 17
- SQLite (built into Android — no extra setup)

## Setup
1. Open folder in Android Studio
2. Sync Gradle → Run on emulator/phone
3. Sign Up with any email + password (min 6 characters)
4. Book a service → check My Bookings

## Package name
`com.homexa.app`

## Database (for teacher)
| Table | Purpose |
|-------|---------|
| `users` | Sign Up / Login accounts |
| `providers` | Service providers (seeded once) |
| `bookings` | User bookings linked by `user_id` |

Main file: `DatabaseHelper.java`
