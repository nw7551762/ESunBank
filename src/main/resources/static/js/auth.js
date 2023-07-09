// 检查用户是否已登录
if (!document.cookie.includes('jwtToken')) {
    // 重定向到登录页面
    window.location.href = '/login';
} else {
    // 发送带有 JWT token 的请求
    fetch('/api/protected-resource', {
        headers: {
            Authorization: 'Bearer ' + getJwtTokenFromCookie()
        }
    })
        .then(response => {
            if (response.ok) {
                // 请求成功，继续处理
            } else {
                // 请求失败，可能是 JWT token 无效或过期
                // 处理错误，例如重新登录或显示错误消息
            }
        })
        .catch(error => {
            // 处理请求错误
        });
}

// 从 cookie 中获取 JWT token
function getJwtTokenFromCookie() {
    const cookies = document.cookie.split(';');
    for (const cookie of cookies) {
        const [name, value] = cookie.trim().split('=');
        if (name === 'jwtToken') {
            return value;
        }
    }
    return null;
}
