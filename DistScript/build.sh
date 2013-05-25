#!/bin/bash

echo ""
echo ""
echo "Building ModelCC distribution package"
echo ""
echo ""
echo "Cleaning previous builds..."
rm -rf ../dist
mkdir ../dist
echo "Previous builds cleaned."
echo ""

echo "Generating local copy..."
cp -rf ../ModelCCExamples ../ModelCC/ res config ../UserManual addcr.sh ../dist
cd ../dist
echo "Local copy generated."
echo ""

echo "Cleaning local copy..."
cd ModelCC
rm -rf bin
cd ..
cd ModelCCExamples
rm -rf bin
cd ..
cd UserManual
make clean
cd ..
echo "Local copy cleaned."
echo ""

echo "Removing .svn rubbish..."
find -name .svn | xargs rm -rf
echo ".svn rubbish removed."
echo ""

javas=`find -name *.java`
javasrc=`find ModelCC/src -name *.java`
javatest=`find ModelCC/test ModelCCExamples/src ModelCCExamples/test -name *.java`
echo "Found `echo $javasrc | wc -w` java source files"
echo "Found `echo $javatest | wc -w` java test files"
echo "Found `echo $javas | wc -w` total java files"
echo ""

echo "Checking TO-DOs..."
af=0
for i in $javas;do
  grep -H TODO "$i"&&af=1
done
if [ $af -eq 1 ];then
  echo ERROR: TO-DOs found!
  exit
fi
echo "TO-DOs not found."
echo ""

echo "Checking headers..."
eq=0
le=`cat res/headerorig | wc -l`
for i in $javas;do
  head "$i" -n $le > aux
  af=0
  diff aux res/headerorig > /dev/null||af=1
  if [ $af -eq 1 ];then
     echo ERROR: INCORRECT HEADER IN $i
     eq=1
  fi
done
if [ $eq -eq 1 ];then
  exit
fi
echo "Headers ok."
echo ""

echo "Checking @author..."
eq=0
au=`cat res/authororig`
for i in $javas;do
  af=0
  cat "$i" | grep -e "$au" > /dev/null&&af=1
  
  if [ $af -eq 0 ];then
     echo ERROR: INCORRECT @AUTHOR IN $i
     eq=1
  fi
done
if [ $eq -eq 1 ];then
  exit
fi
echo "@author's ok."
echo ""

echo "Checking serialVersionUID..."
eq=0
su=`cat res/serialorig`
for i in $javasrc;do
  af=0
  ser=0
  cat "$i" | grep "public enum" > /dev/null||ser=1

  if [ $ser -eq 1 ];then
    ser=0
    cat "$i" | grep "public @interface" > /dev/null||ser=1

    if [ $ser -eq 1 ];then
      ser=0
      cat "$i" | grep "public interface" > /dev/null||ser=1
    
      if [ $ser -eq 1 ];then
        cat "$i" | grep -e "$su" > /dev/null&&af=1

        if [ $af -eq 0 ];then
           echo ERROR: INCORRECT SERIALVERSIONUID IN $i
           eq=1
        fi
      fi
    fi
  fi
done
if [ $eq -eq 1 ];then
  exit
fi
echo "serialVersionUID's ok."
echo ""

echo "Generating new headers..."
minjre=`cat config/minjre`
releasedate=`cat config/releasedate`
version=`cat config/version`
ln=`echo "Version $version ($releasedate).\n\/\/\n\/\/ Requires JRE $minjre or higher."`
cat res/header | sed s/'REPLACETHIS'/"$ln"/g > res/newheader
echo "Headers generated."
echo ""

echo "Changing headers..."
for i in $javas;do
  cat res/newheader > aux
  tail "$i" -n +$le >> aux
  mv aux "$i"
done
echo "Headers changed."
echo ""

echo "Changing @author's..."
aun=`cat res/author`
for i in $javas;do
  cat "$i" | sed s/"$au"/"$aun"/g > aux
  mv aux "$i"
done
echo "@author's changed."
echo ""

echo "Generating new serialVersionUIDs..."
suid=`cat config/serialVersionUID`
cat res/serial | sed s/'REPLACETHIS'/"$suid"/g > res/newserial
echo "serialVersionUIDs generated."
echo ""

echo "Changing serialVersionUIDs..."
sun=`cat res/newserial`
for i in $javasrc;do
  ser=0
  cat "$i" | grep "@serial" > /dev/null&&ser=1
  if [ $ser -eq 1 ];then
    cat "$i" | sed s/"$su"/"$sun"/g > aux
    mv aux "$i"
  fi
done
echo "serialVersionUIDs changed."
echo ""

echo "Checking headers..."
eq=0
le=`cat res/newheader |wc -l`
for i in $javas;do
  head "$i" -n $le > aux
  af=0
  diff aux res/newheader > /dev/null||af=1
  if [ $af -eq 1 ];then
     echo WARNING, INCORRECT HEADER IN $i
     eq=1
  fi
done
if [ $eq -eq 1 ];then
  exit
fi
echo "Headers ok."
echo ""

echo "Checking @author..."
eq=0
for i in $javas;do
  af=0
  cat "$i" | grep "$aun" > /dev/null&&af=1
  if [ $af -eq 0 ];then
     echo ERROR: INCORRECT @AUTHOR IN $i
     eq=1
  fi
done
if [ $eq -eq 1 ];then
  exit
fi
echo "@author's ok."
echo ""

echo "Checking serialVersionUID..."
eq=0
su=`cat res/serialorig`
for i in $javasrc;do
  af=0
  ser=0
  cat "$i" | grep "public enum" > /dev/null||ser=1

  if [ $ser -eq 1 ];then
    ser=0
    cat "$i" | grep "public @interface" > /dev/null||ser=1

    if [ $ser -eq 1 ];then
      ser=0
      cat "$i" | grep "public interface" > /dev/null||ser=1

      if [ $ser -eq 1 ];then
        cat "$i" | grep -e "$sun" > /dev/null&&af=1

        if [ $af -eq 0 ];then
           echo ERROR: INCORRECT SERIALVERSIONUID IN $i
           eq=1
        fi
      fi
    fi
  fi
done
if [ $eq -eq 1 ];then
  exit
fi
echo "serialVersionUID's ok."
echo ""

echo "Generating readme.txt file..."
ln2=`echo "Version $version ($releasedate).\n\nRequires JRE $minjre or higher."`
cat res/readme | sed s/'REPLACETHIS'/"$ln2"/g > res/readme.txt
echo "readme.txt file generated."
echo ""

echo "Adding Carriage Returns..."
for i in $javas config/changelog.txt res/license.txt res/readme.txt;do
  cat $i | ./addcr.sh > aux
  mv aux $i
done
echo "Carriage returns added."
echo ""

echo "Touching software..."
find -name \* | xargs touch
echo "Software touched."
echo ""

echo "Building ModelCC..."
target=`cat config/targetJVM`
cd ModelCC
eq=0
cd src
mkdir ../bin
find -name *.java > list
javac -source $target -target $target -d ../bin @list || eq=1
rm list
#cp -rf * ../bin
#cd ../bin
#find -name *.java | xargs rm -rf
cd ..
cd test
mkdir ../bintest
find -name *.java > list
javac -source $target -target $target -cp ../bin:../../res/junit.jar -d ../bintest @list || eq=1
rm list
#cp -rf * ../bintest
#cd ../bintest
#find -name *.java | xargs rm -rf
cd ..
cd bin
jar cf ModelCC.jar org
cd ..
if [ $eq -eq 1 ];then
  echo "ERROR: BUILD FAILED"
  exit
fi
cp bin/ModelCC.jar ../ModelCC.jar
cd ..
echo "ModelCC built."
echo ""

echo "Testing ModelCC..."
cd ModelCC
eq=0
cd bintest
java -cp .:../bin:../../res/junit.jar:../../res/org.hamcrest.core_1.1.0.v20090501071000.jar org.junit.runner.JUnitCore `find -name *Test.class | sed s/'.class'//g | sed s/'\.\/'/''/g | tr '/' '.'` || eq=1
if [ $eq -eq 1 ];then
  echo "ERROR: JUNITS FAILED"
  exit
fi
cd ..
cd ..
echo "ModelCC tested."
echo ""

echo "Building ModelCCExamples..."
cd ModelCCExamples
eq=0
cd src
mkdir ../bin
find -name *.java > list
javac -source $target -target $target -cp ../../ModelCC.jar:../lib/lwjgl-2.9.0/jar/lwjgl.jar:../lib/lwjgl-2.9.0/jar/lwjgl_util.jar:../lib/lwjgl-2.9.0/jar/jinput.jar:../lib/slick-util/slick-util.jar -d ../bin @list || eq=1
rm list
#cp -rf * ../bin
#cd ../bin
#find -name *.java | xargs rm -rf
cd ..
cd test
mkdir ../bintest
find -name *.java > list
javac -source $target -target $target -cp ../../ModelCC.jar:../bin:../../res/junit.jar -d ../bintest @list || eq=1
rm list
#cp -rf * ../bintest
#cd ../bintest
#find -name *.java | xargs rm -rf
cd ..
cd bin
jar cfm ModelCCExamples.jar ../../res/manifest org
cd ..
if [ $eq -eq 1 ];then
  echo "ERROR: BUILD FAILED"
  exit
fi
cp bin/ModelCCExamples.jar ../ModelCCExamples-build.jar
cd ..
echo "ModelCCExamples built."
echo ""

echo "Testing ModelCCExamples..."
cd ModelCCExamples
eq=0
cd bintest
java -cp .:../../ModelCC.jar:../bin:../../res/junit.jar:../../res/org.hamcrest.core_1.1.0.v20090501071000.jar org.junit.runner.JUnitCore `find -name *Test.class | sed s/'.class'//g | sed s/'\.\/'/''/g | tr '/' '.'` || eq=1
if [ $eq -eq 1 ];then
  echo "ERROR: JUNITS FAILED"
  exit
fi
cd ..
cd ..
echo "ModelCCExamples tested."
echo ""

echo "Bundling ModelCCExamples..."
eq=0
java -jar res/JarSpliceCLI.jar -i ModelCCExamples-build.jar ModelCC.jar ModelCCExamples/lib/lwjgl-2.9.0/jar/lwjgl.jar ModelCCExamples/lib/lwjgl-2.9.0/jar/lwjgl_util.jar ModelCCExamples/lib/lwjgl-2.9.0/jar/jinput.jar ModelCCExamples/lib/slick-util/slick-util.jar -n ModelCCExamples/lib/lwjgl-2.9.0/native/linux/libjinput-linux64.so ModelCCExamples/lib/lwjgl-2.9.0/native/linux/libjinput-linux.so ModelCCExamples/lib/lwjgl-2.9.0/native/linux/liblwjgl64.so ModelCCExamples/lib/lwjgl-2.9.0/native/linux/liblwjgl.so ModelCCExamples/lib/lwjgl-2.9.0/native/linux/libopenal64.so ModelCCExamples/lib/lwjgl-2.9.0/native/linux/libopenal.so ModelCCExamples/lib/lwjgl-2.9.0/native/macosx/libjinput-osx.jnilib ModelCCExamples/lib/lwjgl-2.9.0/native/macosx/liblwjgl.jnilib ModelCCExamples/lib/lwjgl-2.9.0/native/macosx/openal.dylib ModelCCExamples/lib/lwjgl-2.9.0/native/windows/jinput-dx8_64.dll ModelCCExamples/lib/lwjgl-2.9.0/native/windows/jinput-dx8.dll ModelCCExamples/lib/lwjgl-2.9.0/native/windows/jinput-raw_64.dll ModelCCExamples/lib/lwjgl-2.9.0/native/windows/jinput-raw.dll ModelCCExamples/lib/lwjgl-2.9.0/native/windows/lwjgl64.dll ModelCCExamples/lib/lwjgl-2.9.0/native/windows/lwjgl.dll ModelCCExamples/lib/lwjgl-2.9.0/native/windows/OpenAL32.dll ModelCCExamples/lib/lwjgl-2.9.0/native/windows/OpenAL64.dll -o ModelCCExamples.jar -m org.modelcc.examples.test.InteractiveTest -p -Xms256m -Xmx512m || eq=1
if [ $eq -eq 1 ];then
  echo "ERROR: BUNDLING FAILED"
  exit
fi
#TODO rm ModelCCExamples-build.jar
echo "ModelCCExamples bundled."

exit

echo "Building JavaDoc..."
cd ModelCC
cd src
eq=0
javadoc -d ../doc org.modelcc | grep ": warning -" && eq=1
if [ $eq -eq 1 ];then
  echo "ERROR: JAVADOC BUILD FAILED"
  exit
fi
cd ..
mv doc ../
cd ..
echo "JavaDoc built."
echo ""

echo "Cleaning local copy..."
cd ModelCC
rm -rf bin bintest
cd ..
cd ModelCCExamples
ant clean > /dev/null
rm -rf bin bintest
cd ..
echo "Local copy cleaned."
echo ""

echo "Generating manual..."
cp config/version config/releasedate UserManual
cd UserManual
eq=0
make doc.pdf&&eq=1
if [ $eq -eq 0 ];then
   echo ERROR: MANUAL DOES NOT BUILD
   exit
fi
cp doc.pdf ../UserManual.pdf
make clean
cd ..
echo "Manual generated."
echo ""

#TODO TODOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOOO
exit

echo "Organizing software..."
mcdirs=`echo modelcc-$version-src`
mcdirb=`echo modelcc-$version`
mkdir $mcdirs
mkdir $mcdirb
mv ModelCC ModelCCExamples $mcdirs
mv $mcjar $mcdirb
mv $mcejar lib $mcedirb
mv manual $mcdirs
cp manual.pdf $mcdirb
#TODO FIX THIS mv javadoc doc

cp config/changelog.txt res/license.txt res/readme.txt $mcdirs
cp config/changelog.txt res/license.txt res/readme.txt $mcdirb
cp config/changelog.txt res/license.txt res/readme.txt $mcedirs
cp config/changelog.txt res/license.txt res/readme.txt $mcedirb
echo "Software organized."
echo ""

echo "Touching software..."
find -name \* | xargs touch
echo "Software touched."
echo ""

echo "Building source package..."
zip -r -9 $mcdirs.zip $mcdirs > /dev/null
zip -r -9 $mcedirs.zip $mcedirs > /dev/null
echo "Source package built."
echo ""

echo "Building binary package..."
zip -r -9 $mcdirb.zip $mcdirb > /dev/null
zip -r -9 $mcedirb.zip $mcedirb > /dev/null
echo "Binary package built."
echo ""


#echo "Building documentation package..."
#cd doc
#mcdoc=`echo modelcc-$version-doc.zip`
#zip -r -9 ../$mcdoc * > /dev/null
#cd ..
#md5sum $mcdoc > $mcdoc.md5sum
#echo "Documentation package built."
#echo ""

echo "Calculating md5sum..."
md5sum $mcdirb.zip $mcedirb.zip $mcdirs.zip $mcedirs.zip > modelcc-$version.md5sum
echo "md5sum calculated."
echo ""

echo "Touching software..."
touch $mcdirb.zip $mcedirb.zip $mcdirs.zip $mcedirs.zip modelcc-$version.md5sum 
# $mcdoc
echo "Software touched."
echo ""

echo "Please, check:"
echo "------------"
echo "readme.txt:"
echo ""
cat res/readme.txt
echo ""
echo "------------"
echo "HEADER:"
echo ""
cat res/newheader
echo ""
echo "------------"
echo "SERIAL:"
echo ""
cat res/newserial
echo ""
echo "------------"


echo "Cleaning build..."
rm -rf res config javadoc
#mv doc modelcc-$version-doc
echo "Build cleaned."
echo ""

