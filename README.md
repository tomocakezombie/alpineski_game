# ALPENSKI

```
　　　＃＃　　　　　＃＃　　　　　　　＃＃＃＃＃　　　　　＃＃＃＃　　　　＃＃　　＃＃　　　＃＃＃＃＃＃
　　＃＃＃＃　　　　＃＃　　　　　　　＃＃　　＃＃　　　　　＃＃　　　　　＃＃＃　＃＃　　　＃＃　　　　
　＃＃　　＃＃　　　＃＃　　　　　　　＃＃　　＃＃　　　　　＃＃　　　　　＃＃＃＃＃＃　　　＃＃　　　　
　＃＃＃＃＃＃　　　＃＃　　　　　　　＃＃＃＃＃　　　　　　＃＃　　　　　＃＃＃＃＃＃　　　＃＃＃＃　　
　＃＃　　＃＃　　　＃＃　　　　　　　＃＃　　　　　　　　　＃＃　　　　　＃＃　＃＃＃　　　＃＃　　　　
　＃＃　　＃＃　　　＃＃　　　　　　　＃＃　　　　　　　　　＃＃　　　　　＃＃　　＃＃　　　＃＃　　　　
　＃＃　　＃＃　　　＃＃＃＃＃＃　　　＃＃　　　　　　　　＃＃＃＃　　　　＃＃　　＃＃　　　＃＃＃＃＃＃
```

ターミナル上で動作するJava製スキーゲームです。旗の間をくぐり抜け、障害物を避けながらハイスコアを目指します。

---

## ゲーム概要

プレイヤー（`＠`）を操作して山の斜面を滑走します。旗（`Ｆ`）の間をくぐり抜け、落下してくる障害物（`＊`）を避けながら、できるだけ長い距離を走破するのが目的です。

- **HP**：最大3。障害物への衝突または旗の取り逃がしでダメージを受けます
- **スコア**：走行距離（m）がそのままスコアになります
- **ゲームオーバー**：HPがゼロになるとゲームオーバーです

---

## 難易度

| モード | 説明 |
|--------|------|
| **NORMAL** | 標準的な速度と障害物量 |
| **HARD** | 速度が速く、障害物も多い |
| **ENDLESS** | 時間経過で難易度が上昇。雪崩イベントが発生する |

### 雪崩イベント（ENDLESSモード限定）

「気を付けろ！」の警告が出たら雪崩の前兆です。左右どちらかから大量の障害物が降ってきます。雪崩に当たると**一発でゲームオーバー**になります。

---

## 操作方法

| キー | 動作 |
|------|------|
| `↑` / `W` | 上に移動 |
| `↓` / `S` | 下に移動 |
| `←` / `A` | 左に移動 |
| `→` / `D` | 右に移動 |
| `Enter` | 決定・次へ |

---

## 動作環境

- Java（JDKがインストールされていること）
- bash が使用できるLinux/macOS環境

---

## ビルドと起動

```bash
# コンパイル
javac *.java

# 起動（read_key.sh でキー入力をJavaに渡す）
bash read_key.sh | java Model
```

---

## ファイル構成

```
alpenski/
├── Model.java                # エントリポイント・MVCのModel
├── ModelPrepareStart.java    # スタート前演出の処理
├── ModelStart.java           # タイトル画面の処理
├── ModelPreparePlaying.java  # ゲーム開始準備の処理
├── ModelPlaying.java         # ゲームプレイ中のメインロジック
├── ModelWrite.java           # スコア書き込み処理
├── ModelGameEnd.java         # ゲームオーバー画面の処理
├── ConsoleView.java          # 画面描画（View）
├── ConsoleController.java    # 入力・タイマー管理（Controller）
├── Player.java               # プレイヤークラス
├── Obstacle.java             # 障害物クラス
├── Flag.java                 # 旗クラス
├── Avalanche.java            # 雪崩管理クラス
├── GameState.java            # ゲーム状態管理
├── GameDifficulty.java       # 難易度管理
├── Score.java                # スコア管理
├── read_key.sh               # キー入力をJavaに渡すシェルスクリプト
└── ReadFiles/                # ゲーム内で表示するテキストアート
    ├── LOGO1.txt
    ├── LOGO2.txt
    ├── MOUNTAIN.txt
    └── ...
```

---

## 設計

MVCアーキテクチャを採用しています。

- **Model**：ゲームロジック全体を管理。各フェーズ（`ModelStart`, `ModelPlaying` 等）に処理を委譲
- **View**（`ConsoleView`, `GameMapView`）：ターミナルへの描画を担当
- **Controller**（`ConsoleController`）：キー入力の受け取りとSwing Timerによるゲームループを管理

---

## 開発者

t23cs014
