mkdir temp
cp -r ./PongGame/shaders ./temp/
cp ./PongGame/target/PongGame-1.0-SNAPSHOT.jar ./temp/
cp -r ./PongGame/target/natives ./temp/
cp -r ./PongGame/target/lib ./temp/
cd temp
touch run.sh
echo "java -jar PongGame-1.0-SNAPSHOT.jar -Djava.library.path=./natives/" > run.sh
cd ..
mv temp PongGame-release
zip -r PongGame-release.zip PongGame-release
rm -rf PongGame-release

