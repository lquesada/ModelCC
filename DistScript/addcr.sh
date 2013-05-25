#!/bin/bash
tr -d '\r' | tr '\n' 'ÿ' | sed s/'ÿ'/"öÿ"/g | tr 'öÿ' '\r\n'
