(ns haiku.runner
  (:require [doo.runner :refer-macros [doo-tests]]
            [haiku.core-test]))

(doo-tests 'haiku.core-test)
