(ns interuptible.core
  (:require [clojure.repl :refer [set-break-handler!]]))

(defn interruptibly
  "Calls (f interrupted? & args) catching interrupts (e.g. ctrl-c)
  interrupted? is a callback which may be called by f and returns true if an interrupt has occured"
  [f & args]
  (let [interrupted? (atom false)
        t (Thread/currentThread)]

    ; deletes a possibly previously set interrupt
    (Thread/interrupted)

    (set-break-handler! (fn [_]
                          (.interrupt t)
                          (reset! interrupted? true)))
    (try
      (if-not @interrupted?
        (apply f (cons (fn [] @interrupted?) args)))
      (catch InterruptedException e))))

(defn infinitely
  "Calls (f interrupted? & args) infinitly sleeping msecs milliseconds between each call
   until an interrupt (e.g. crtl-c) occurs.
   interrupted? is a callback which may be called by f and returns true if an interrupt has occured.
   therefor allowing f to return gracefully"
  [msecs f & args]
  (interruptibly
    (fn [interrupted?]
      (loop []
        (when-not (interrupted?)
          (apply f (cons interrupted? args))
          (when-not (interrupted?)
            (Thread/sleep msecs)
            (recur)))))))
