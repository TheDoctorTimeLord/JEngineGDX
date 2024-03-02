# TODO исправить проблему разницы timestamp из-за чего jengine.jar всегда обновляется, даже если нет ничего нового

if [ -z "$1" ] || [ "$1" = "-h" ] || [ "$1" = "--help" ];
then
  echo "usage: updateLocalLibs.sh <path> [-h][--help]
Update JEngine version by local sources. The script will download
a latest version of the library. If your sources version has local
changes then the script will throw error. You must have installed
Maven.

 <path>     path to local JEnginge lib sources
 -h --help  write help for usage"
  return 0
fi

if ! mvn -version > /dev/null
then
  echo "Please install Maven. Command 'mvn' is not found"
  return 1
fi

path=$1
script_path=${PWD}

if [ ! -d "$path" ];
then
  echo "Path $path does not exist!"
  return 2
fi

cd "$path"

if [ ! -d .git ] || [ ! "${PWD##*/}" = "JEngine" ];
then
  echo "$path directory is not JEngine sources"
  return 3
fi

git checkout main > /dev/null
git pull > /dev/null
mvn clean > /dev/null
mvn package > /dev/null
cp -p "$(find ./ -name 'jengine-*-with-dependencies*')" "$script_path/libs/jengine.jar"

