# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## ビルドコマンド

```bash
./gradlew assembleDebug
```

## プロジェクト概要

WidgetLearnは、Androidアプリウィジェットの実装を学習するためのサンプルアプリです。Jetpack ComposeとGlanceを使用してノートアプリとホーム画面ウィジェットを実装しています。

## アーキテクチャ

### 技術スタック
- **UI**: Jetpack Compose + Material3
- **ウィジェット**: Glance (App Widget)
- **DI**: Hilt
- **DB**: Room
- **設定保存**: DataStore Preferences
- **ナビゲーション**: Navigation Compose

### レイヤー構成

```
app/src/main/java/com/example/widgetlearn/
├── data/           # データ層
│   ├── di/         # DatabaseModule (Room DI)
│   └── local/      # Room (NoteEntity, NoteDao, AppDatabase)
├── navigation/     # NavRoutes (sealed class)
├── ui/             # 画面層
│   ├── home/       # ホーム画面 (ノート一覧)
│   ├── notedetail/ # ノート詳細画面
│   └── theme/      # Composeテーマ
└── widget/         # ウィジェット関連
    ├── config/     # ウィジェット設定画面・ViewModel・Repository
    └── di/         # WidgetModule (DataStore DI)
```

### Glanceウィジェットの構成

Glanceウィジェットはアプリのコンテキスト外で動作するため、Hiltの通常のインジェクションが使えません。`WidgetEntryPoint`インターフェースを通じて`EntryPointAccessors`で依存関係を取得します：

- `SimpleColorWidget`: ウィジェットのUI定義 (`GlanceAppWidget`)
- `SimpleColorWidgetReceiver`: ブロードキャストレシーバー
- `WidgetEntryPoint`: Hiltエントリーポイント（`NoteDao`と`WidgetConfigRepository`を提供）
- `WidgetConfigActivity`: ウィジェット追加時の設定画面
- `WidgetConfigRepository`: DataStoreを使用したウィジェット設定の永続化

### データフロー

1. **ノートデータ**: `NoteDao` (Room) → `ViewModel` (StateFlow) → Compose UI
2. **ウィジェット設定**: `WidgetConfigRepository` (DataStore) → `SimpleColorWidget`
3. **ウィジェットからアプリ起動**: Intent経由で`noteId`を渡し、`MainActivity`で対象ノートに直接遷移
