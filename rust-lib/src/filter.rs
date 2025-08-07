#[unsafe(no_mangle)]
pub extern "system" fn Java_frc_lib_RustBindings_cutoffFilter(
    _env: *mut std::ffi::c_void,
    _class: *mut std::ffi::c_void,
    value: f64,
    max: f64,
    min: f64,
) -> f64 {
    if value > max {
        max
    } else if value < min {
        min
    } else {
        value
    }
}
