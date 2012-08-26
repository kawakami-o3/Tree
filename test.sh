#!/bin/sh
set -x
ant
java  -cp ./tree.jar tree.BinaryTree
#java  -cp ./tree.jar tree.RedBlack
#java  -cp ./tree.jar fn.Flib
