#!/bin/bash

# 只在包含 <modules> 的目录中执行 versions:update-child-modules
update_versions() {
    local dir="$1"
    if grep -q '<modules>' "$dir/pom.xml" 2>/dev/null; then
        echo "Updating child modules in $dir"
        (cd "$dir" && mvn -N versions:update-child-modules -DgenerateBackupPoms=false)
    fi

    # 递归处理子目录
    for subdir in "$dir"/*/; do
        if [ -f "${subdir}pom.xml" ]; then
            update_versions "$subdir"
        fi
    done
}

# 从当前目录开始
update_versions "$(pwd)"