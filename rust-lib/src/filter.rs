use std::ffi::c_void;

#[unsafe(no_mangle)]
pub extern "system" fn Java_frc_lib_RustFilter_cutoffFilter(
    _env: *mut c_void,
    _class: *mut c_void,
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

#[unsafe(no_mangle)]
pub extern "system" fn Java_frc_lib_RustFilter_powerCurce(
    _env: *mut c_void,
    _class: *mut c_void,
    value: f64,
    power: f64,
) -> f64 {
    // Preserve the sign
    let sign = if value < 0.0 { -1.0 } else { 1.0 };
    // Absolute value
    let abs_val = value.abs();

    // Computer abs_val * power
    let powered_value = abs_val.powf(power);

    if powered_value.is_infinite() {
        0.0
    } else {
        powered_value * sign
    }
}
