#!/bin/bash
tr -d '\r' | tr '\n' '�' | sed s/'�'/"��"/g | tr '��' '\r\n'
