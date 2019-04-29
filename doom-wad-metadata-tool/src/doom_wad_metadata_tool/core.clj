(ns doom-wad-metadata-tool.core
  (:require [clojure.spec.alpha :as s]
            [cheshire.core :as c]
            [clojure.java.io :as io]
            [yaml.core :as yaml]
            [clojure.edn :as edn])
  (:import (java.io PushbackReader)))


;; Spec

;; common
(def non-negative? (complement neg-int?))

(s/def ::name string?)
(s/def ::alternate-name string?)
(s/def ::file-name string?)
(s/def ::description string?)
(s/def ::short-name (s/and string? #(re-matches #"E\dM\d|MAP\d\d" %)))
(s/def ::authors (s/coll-of ::name))
(s/def ::sound-track string?)
(s/def ::par-time (s/and string? #(re-matches #"\d:\d\d" %)))
(s/def ::secret boolean?)
(s/def ::secret-exit boolean?)
(s/def ::things non-negative?)
(s/def ::vertexes non-negative?)
(s/def ::linedefs non-negative?)
(s/def ::sidedefs non-negative?)
(s/def ::sectors non-negative?)

;; statistics
(s/def ::map-data (s/keys :req-un [::things ::vertexes ::linedefs ::sidedefs ::sectors]))
(s/def ::statistics (s/keys :req-un [::map-data]))

;; levels
(s/def ::level (s/keys :req-un [::name ::short-name ::authors ::sound-track ::statistics]
                       :opt-un [::alternate-name ::par-time ::secret ::secret-exit]))
(s/def ::levels (s/coll-of ::level))

;; episodes
(s/def ::episode-short-name #{:e1 :e2 :e3 :e4 :secret})
(s/def ::episode-def (s/keys :req-un [::name ::levels]))
(s/def ::episode (s/map-of ::episode-short-name ::episode-def))
(s/def ::episodes (s/coll-of ::episode))

;; iwads
(s/def ::iwad-name #{:doom :doom2 :tnt :plutonia})
(s/def ::iwad-def (s/keys :req-un [::name ::file-name ::episodes]))
(s/def ::iwad (s/map-of ::iwad-name ::iwad-def))
(s/def ::iwads-name #{:iwads})
(s/def ::iwads (s/map-of ::iwads-name (s/coll-of ::iwad)))


;; Transforming

;; edn
(defn read-edn-file
  [file-name]
  (edn/read (PushbackReader. (io/reader file-name))))

;; json
(defn convert-to-json
  [iwads]
  (c/generate-string iwads {:pretty true}))

(defn write-json-to-file
  [iwads file-name]
  (c/generate-stream iwads (io/writer file-name)))

;; yaml
(defn convert-to-yaml
  [iwads]
  (yaml/generate-string iwads))

(defn write-yaml-to-file
  [iwads file-name]
  (with-open [w (io/writer file-name)]
    (.write w ^String (convert-to-yaml iwads))
    w))
