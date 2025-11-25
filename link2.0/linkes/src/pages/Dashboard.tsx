import { useNavigate } from 'react-router-dom';
import { FileText, Link2, QrCode, BarChart3, Eye, Plus } from 'lucide-react';

export function Dashboard() {
  const navigate = useNavigate();

  const stats = [
    { label: 'Bio Pages', value: '3', icon: FileText, color: 'from-blue-500 to-cyan-500' },
    { label: 'Links Encurtados', value: '24', icon: Link2, color: 'from-purple-500 to-pink-500' },
    { label: 'QR Codes', value: '12', icon: QrCode, color: 'from-orange-500 to-red-500' },
    { label: 'Total de Cliques', value: '1,234', icon: BarChart3, color: 'from-green-500 to-emerald-500' }
  ];

  const quickActions = [
    {
      title: 'Criar Bio Page',
      description: 'Monte sua página de links personalizada',
      icon: FileText,
      action: () => navigate('/bio'),
      color: 'from-blue-500 to-cyan-500'
    },
    {
      title: 'Encurtar Link',
      description: 'Transforme URLs longas em links curtos',
      icon: Link2,
      action: () => navigate('/links'),
      color: 'from-purple-500 to-pink-500'
    },
    {
      title: 'Gerar QR Code',
      description: 'Crie QR codes personalizados',
      icon: QrCode,
      action: () => navigate('/qrcode'),
      color: 'from-orange-500 to-red-500'
    }
  ];

  const recentActivity = [
    { type: 'bio', title: 'Minha Bio Principal', views: 156, time: 'há 2 horas' },
    { type: 'link', title: 'link.io/promo2024', clicks: 89, time: 'há 5 horas' },
    { type: 'qr', title: 'QR Code - Evento', scans: 45, time: 'há 1 dia' }
  ];

  return (
    <div className="min-h-screen bg-gradient-to-br from-indigo-50 via-purple-50 to-pink-50">
      {/* Header */}
      <header className="bg-white border-b border-gray-200 sticky top-0 z-10 shadow-sm">
        <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-4">
          <div className="flex items-center justify-between">
            <div className="flex items-center gap-3">
              <div className="p-2 bg-gradient-to-r from-indigo-600 to-purple-600 rounded-xl">
                <Link2 className="w-6 h-6 text-white" />
              </div>
              <h1 className="text-gray-900">Dashboard</h1>
            </div>
            <button
              onClick={() => navigate('/')}
              className="px-4 py-2 text-gray-600 hover:text-gray-900 transition-colors"
            >
              Sair
            </button>
          </div>
        </div>
      </header>

      <div className="max-w-7xl mx-auto px-4 sm:px-6 lg:px-8 py-8">
        {/* Welcome Section */}
        <div className="mb-8">
          <h2 className="text-gray-900 mb-2">Bem-vindo de volta! 👋</h2>
          <p className="text-gray-600">Aqui está um resumo da sua atividade</p>
        </div>

        {/* Stats Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-4 gap-6 mb-8">
          {stats.map((stat, index) => (
            <div
              key={index}
              className="bg-white rounded-2xl p-6 shadow-lg border border-gray-100 hover:shadow-xl transition-all"
            >
              <div className="flex items-start justify-between mb-4">
                <div className={`p-3 rounded-xl bg-gradient-to-r ${stat.color}`}>
                  <stat.icon className="w-6 h-6 text-white" />
                </div>
              </div>
              <div className="text-3xl text-gray-900 mb-1">{stat.value}</div>
              <div className="text-sm text-gray-600">{stat.label}</div>
            </div>
          ))}
        </div>

        {/* Quick Actions */}
        <div className="mb-8">
          <h3 className="text-gray-900 mb-4">Ações Rápidas</h3>
          <div className="grid md:grid-cols-3 gap-6">
            {quickActions.map((action, index) => (
              <button
                key={index}
                onClick={action.action}
                className="group bg-white rounded-2xl p-6 shadow-lg border border-gray-100 hover:shadow-2xl transition-all hover:-translate-y-1 text-left"
              >
                <div className={`inline-flex p-3 rounded-xl bg-gradient-to-r ${action.color} mb-4 group-hover:scale-110 transition-transform`}>
                  <action.icon className="w-6 h-6 text-white" />
                </div>
                <h4 className="text-gray-900 mb-2">{action.title}</h4>
                <p className="text-sm text-gray-600 mb-4">{action.description}</p>
                <div className="flex items-center gap-2 text-purple-600">
                  <Plus className="w-4 h-4" />
                  <span className="text-sm">Criar agora</span>
                </div>
              </button>
            ))}
          </div>
        </div>

        {/* Recent Activity */}
        <div>
          <h3 className="text-gray-900 mb-4">Atividade Recente</h3>
          <div className="bg-white rounded-2xl shadow-lg border border-gray-100 overflow-hidden">
            {recentActivity.map((item, index) => (
              <div
                key={index}
                className="p-6 hover:bg-gray-50 transition-colors border-b border-gray-100 last:border-b-0"
              >
                <div className="flex items-center justify-between">
                  <div className="flex items-center gap-4">
                    <div className="p-2 bg-purple-100 rounded-lg">
                      {item.type === 'bio' && <FileText className="w-5 h-5 text-purple-600" />}
                      {item.type === 'link' && <Link2 className="w-5 h-5 text-purple-600" />}
                      {item.type === 'qr' && <QrCode className="w-5 h-5 text-purple-600" />}
                    </div>
                    <div>
                      <h4 className="text-gray-900">{item.title}</h4>
                      <p className="text-sm text-gray-500">{item.time}</p>
                    </div>
                  </div>
                  <div className="flex items-center gap-4">
                    <div className="text-right">
                      <div className="text-gray-900">
                        {item.views && `${item.views} visualizações`}
                        {item.clicks && `${item.clicks} cliques`}
                        {item.scans && `${item.scans} escaneamentos`}
                      </div>
                    </div>
                    <Eye className="w-5 h-5 text-gray-400" />
                  </div>
                </div>
              </div>
            ))}
          </div>
        </div>
      </div>
    </div>
  );
}
