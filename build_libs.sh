#!/usr/bin/env bash
# Written in [Amber](https://amber-lang.com/)
# version: 0.4.0-alpha
# date: 2025-08-07 14:16:19
replace__0_v0() {
    local source=$1
    local search=$2
    local replace=$3
    __AF_replace0_v0="${source//${search}/${replace}}";
    return 0
}
replace_regex__2_v0() {
    local source=$1
    local search=$2
    local replace=$3
    local extended=$4
            replace__0_v0 "${search}" "/" "\/";
        __AF_replace0_v0__16_18="${__AF_replace0_v0}";
        search="${__AF_replace0_v0__16_18}"
        replace__0_v0 "${replace}" "/" "\/";
        __AF_replace0_v0__17_19="${__AF_replace0_v0}";
        replace="${__AF_replace0_v0__17_19}"
        if [ ${extended} != 0 ]; then
            # GNU sed versions 4.0 through 4.2 support extended regex syntax,
            # but only via the "-r" option; use that if the version information
            # contains "GNU sed".
             re='\bCopyright\b.+\bFree Software Foundation\b'; [[ $(sed --version 2>/dev/null) =~ $re ]] ;
            __AS=$?
            local flag=$(if [ $(echo $__AS '==' 0 | bc -l | sed '/\./ s/\.\{0,1\}0\{1,\}$//') != 0 ]; then echo "-r"; else echo "-E"; fi)
            __AMBER_VAL_0=$( echo "${source}" | sed "${flag}" -e "s/${search}/${replace}/g" );
            __AS=$?;
            __AF_replace_regex2_v0="${__AMBER_VAL_0}";
            return 0
else
            __AMBER_VAL_1=$( echo "${source}" | sed -e "s/${search}/${replace}/g" );
            __AS=$?;
            __AF_replace_regex2_v0="${__AMBER_VAL_1}";
            return 0
fi
}
split__3_v0() {
    local text=$1
    local delimiter=$2
    __AMBER_ARRAY_2=();
    local result=("${__AMBER_ARRAY_2[@]}")
     IFS="${delimiter}" read -rd '' -a result < <(printf %s "$text") ;
    __AS=$?
    __AF_split3_v0=("${result[@]}");
    return 0
}
join__6_v0() {
    local list=("${!1}")
    local delimiter=$2
    __AMBER_VAL_3=$( IFS="${delimiter}" ; echo "${list[*]}" );
    __AS=$?;
    __AF_join6_v0="${__AMBER_VAL_3}";
    return 0
}
escape_non_glob_chars__41_v0() {
    local path=$1
    replace_regex__2_v0 "${path}" "\([^*?/]\)" "\\\\\1" 0;
    __AF_replace_regex2_v0__86_12="${__AF_replace_regex2_v0}";
    __AF_escape_non_glob_chars41_v0="${__AF_replace_regex2_v0__86_12}";
    return 0
}
file_glob_all__42_v0() {
    local paths=("${!1}")
    local combined=""
    if [ $(echo "${#paths[@]}" '==' 1 | bc -l | sed '/\./ s/\.\{0,1\}0\{1,\}$//') != 0 ]; then
        escape_non_glob_chars__41_v0 "${paths[0]}";
        __AF_escape_non_glob_chars41_v0__95_20="${__AF_escape_non_glob_chars41_v0}";
        combined="${__AF_escape_non_glob_chars41_v0__95_20}"
else
        __AMBER_ARRAY_4=();
        local items=("${__AMBER_ARRAY_4[@]}")
        for item in "${paths[@]}"; do
            escape_non_glob_chars__41_v0 "${item}";
            __AF_escape_non_glob_chars41_v0__99_20="${__AF_escape_non_glob_chars41_v0}";
            item="${__AF_escape_non_glob_chars41_v0__99_20}"
            __AMBER_ARRAY_5=("${item}");
            items+=("${__AMBER_ARRAY_5[@]}")
done
        join__6_v0 items[@] " ";
        __AF_join6_v0__102_20="${__AF_join6_v0}";
        combined="${__AF_join6_v0__102_20}"
fi
    __AMBER_VAL_6=$( eval "for file in ${combined}; do [ -e \"\$file\" ] && echo \"\$file\"; done" );
    __AS=$?;
if [ $__AS != 0 ]; then
__AF_file_glob_all42_v0=()
return $__AS
fi;
    local files="${__AMBER_VAL_6}"
    split__3_v0 "${files}" "
";
    __AF_split3_v0__105_12=("${__AF_split3_v0[@]}");
    __AF_file_glob_all42_v0=("${__AF_split3_v0__105_12[@]}");
    return 0
}
__0_RED="\e[31m"
__1_GREEN="\e[32m"
__2_BLUE="\e[34m"
__3_RESET="\e[0m"
echo_color__46_v0() {
    local text=$1
    local color=$2
    # I'm not sure why but I need to do the reset on a separate concatenation
    __6_text="${color}${text}"
     echo -e "${__6_text}"${__3_RESET};
    __AS=$?
}
# List of target names
__AMBER_ARRAY_7=("x86_64-unknown-linux-gnu" "armv7-unknown-linux-gnueabihf");
__4_architectures=("${__AMBER_ARRAY_7[@]}")
# List of names for each target in the same order
__AMBER_ARRAY_8=("x86_64" "armv7");
__5_arch_names=("${__AMBER_ARRAY_8[@]}")
# Builds each architecture
i=0;
for arch in "${__4_architectures[@]}"; do
    echo_color__46_v0 "Building Linux-${__5_arch_names[${i}]}..." "${__2_BLUE}";
    __AF_echo_color46_v0__23_5="$__AF_echo_color46_v0";
    echo "$__AF_echo_color46_v0__23_5" > /dev/null 2>&1
     sleep 1 ;
    __AS=$?
     cross build --target ${arch} --release --manifest-path rust-lib/Cargo.toml ;
    __AS=$?;
if [ $__AS != 0 ]; then
        echo_color__46_v0 "${__5_arch_names[${i}]} build failed" "${__0_RED}";
        __AF_echo_color46_v0__28_9="$__AF_echo_color46_v0";
        echo "$__AF_echo_color46_v0__28_9" > /dev/null 2>&1
fi
    (( i++ )) || true
done
__7_path="rust-lib/target/x86_64-unknown-linux-gnu/release/*.so"
__AMBER_ARRAY_9=("${__7_path}");
file_glob_all__42_v0 __AMBER_ARRAY_9[@];
__AS=$?;
__AF_file_glob_all42_v0__34_19=("${__AF_file_glob_all42_v0[@]}");
__8_files=("${__AF_file_glob_all42_v0__34_19[@]}")
echo "${__8_files[@]}"
for file in "${__8_files[@]}"; do
     cp ${file} src/main/java/frc/lib/linux-x86_64/ ;
    __AS=$?
done
__9_path="rust-lib/target/armv7-unknown-linux-gnueabihf/release/*.so"
__AMBER_ARRAY_10=("${__9_path}");
file_glob_all__42_v0 __AMBER_ARRAY_10[@];
__AS=$?;
__AF_file_glob_all42_v0__44_19=("${__AF_file_glob_all42_v0[@]}");
__10_files=("${__AF_file_glob_all42_v0__44_19[@]}")
echo "${__10_files[@]}"
for file in "${__10_files[@]}"; do
     cp ${file} src/main/java/frc/lib/armv7/ ;
    __AS=$?
done
